"use client"

import { ReactNode } from "react";
import { SweFormLayoutFooter } from "./components/footer/SweFormLayoutFooter";
import { SweToolbar } from "./components/toolbar/SweToolbar";
import "./swe-page-layout.scss";

interface SwePageLayoutProps {
    title: string;
    className?: string;
    rightSideActionButtons?: ReactNode;
    pickManyMode?: boolean;
    numberOfPickedRecords?: number;
    onDeselect?: () => void;
    onDelete?: () => void;
    children?: ReactNode;
    onSave?: () => void;
    onCancel?: () => void;
    isPending?: boolean;
}

export const SwePageLayout = ({
    title,
    className = "",
    rightSideActionButtons,
    pickManyMode = false,
    numberOfPickedRecords = 0,
    onDeselect,
    onDelete,
    children,
    onSave,
    onCancel,
    isPending = false,
}: SwePageLayoutProps) => {
    return (
        <div className={`swe_page_layout ${className}`} data-testid="swe-page-layout">
            <SweToolbar
                title={title}
                rightSideActionButtons={rightSideActionButtons}
                pickManyMode={pickManyMode}
                numberOfPickedRecords={numberOfPickedRecords}
                onDeselect={onDeselect}
                onDelete={onDelete}
            />

            <div className="swe_page_content" data-testid="swe-page-content">
                {children}
            </div>

            <SweFormLayoutFooter
                onSave={onSave}
                onCancel={onCancel}
                isPending={isPending}
            />
        </div>
    );
}