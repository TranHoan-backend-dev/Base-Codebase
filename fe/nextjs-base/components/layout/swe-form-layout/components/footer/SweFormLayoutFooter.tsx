"use client"

import { Button, Spinner } from "@heroui/react";
import "./swe-footer.scss";
import { useTranslations } from "next-intl";

interface SweFormLayoutFooterProps {
    onSave?: () => void;
    onCancel?: () => void;
    isPending?: boolean;
}

export const SweFormLayoutFooter = ({
    onSave,
    onCancel,
    isPending = false,
}: SweFormLayoutFooterProps) => {
    let translator: any = null;
    try {
        // eslint-disable-next-line react-hooks/rules-of-hooks
        translator = useTranslations("popup");
    } catch {
        translator = null;
    }

    const t = (key: string): string => {
        if (translator) {
            try {
                return translator(key);
            } catch {
                // Fallback
            }
        }
        const defaultTranslations: Record<string, string> = {
            "cancel": "Hủy",
            "save": "Lưu"
        };
        return defaultTranslations[key] || key;
    };

    return (
        <div className="swe_form_layout_footer flex flex-row-reverse gap-2" data-testid="swe-form-layout-footer">
            <Button variant="outline" onPress={onCancel} data-testid="swe-form-footer-cancel-btn">
                {t("cancel")}
            </Button>
            <Button variant="primary" onPress={onSave} isPending={isPending} data-testid="swe-form-footer-save-btn">
                {({ isPending }) => (
                    <>
                        {isPending ? <Spinner color="current" size="sm" /> : null}
                        {t("save")}
                    </>
                )}
            </Button>
        </div>
    );
}