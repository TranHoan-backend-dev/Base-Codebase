"use client"

import { BackIcon } from "@/components/icons";
import { ReactNode } from "react";
import { Button } from "@heroui/react";
import { TrashBin } from "@gravity-ui/icons";
import "./swe-toolbar.scss";
import "@/styles/custom/space.scss"

import { useTranslations } from "next-intl";
import viMessages from "@/messages/vi.json";

interface ToolbarProps {
    className?: string;
    title: string;
    rightSideActionButtons?: ReactNode;
    pickManyMode?: boolean; // chế độ khi người dùng click nhiều bản ghi
    numberOfPickedRecords?: number; // số bản ghi active
    onDeselect?: () => void;
    onDelete?: () => void;
}

export const SweToolbar = ({
    className = "",
    title = "",
    rightSideActionButtons,
    pickManyMode = false,
    numberOfPickedRecords,
    onDeselect,
    onDelete,
}: ToolbarProps) => {
    let translator: any = null;
    try {
        // eslint-disable-next-line react-hooks/rules-of-hooks
        translator = useTranslations("toolbar");
    } catch {
        translator = null;
    }

    const t = (key: string, values?: any): string => {
        if (translator) {
            try {
                return translator(key, values);
            } catch {
                // Fallback
            }
        }
        const defaultTranslations: Record<string, string> = viMessages.toolbar;
        let template = defaultTranslations[key] || key;
        if (values) {
            Object.keys(values).forEach(k => {
                template = template.replace(`{${k}}`, String(values[k]));
            });
        }
        return template;
    };

    return (
        <div className={`swe_toolbar flex items-center justify-between px-4 py-3 ${className}`} data-testid="swe-toolbar">
            {/* Toolbar trai */}
            <div className="toolbar_left flex gap-2 justify-center items-center" data-testid="swe-toolbar-left">
                {pickManyMode && numberOfPickedRecords ? (
                    <div className="flex items-center gap-2">
                        <span data-testid="swe-toolbar-selected-count">{t("selected", { count: numberOfPickedRecords })}</span>
                        <Button
                            variant="ghost"
                            className="text-danger px-0"
                            onPress={onDeselect}
                            data-testid="swe-toolbar-deselect-btn"
                        >
                            {t("deselect")}
                        </Button>
                        <Button
                            variant="danger"
                            onPress={onDelete}
                            data-testid="swe-toolbar-delete-btn"
                        >
                            <TrashBin />
                            {t("delete")}
                        </Button>
                    </div>
                ) : (
                    <>
                        <BackIcon className="swe_back_btn" data-testid="swe-toolbar-back-icon" />
                        <span className="swe_toolbar_title" data-testid="swe-toolbar-title">{title}</span>
                    </>
                )}
            </div>

            {/* Toolbar phai */}
            <div className="toolbar_right flex gap-2" data-testid="swe-toolbar-right">
                {rightSideActionButtons}
            </div>
        </div>
    );
}